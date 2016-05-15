package java7;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class LPath {
	String ParentPath = "E:\\360Downloads";
	String PATH = "E:\\360Downloads\\test2.txt";

	/**
	 * java 7 中少用file类 基本改成path 通过 file.topath path.tofile来保持兼容
	 */
	@Test
	public void testPath() {
		// 创建一个path
		Path path2 = Paths.get(PATH);
		System.out.println(path2.toString());
		//
		Path path3 = FileSystems.getDefault().getPath(PATH);
		System.out.println(path3.toString());
		Path path4 = Paths.get(".//MR-FOR-WORK");
		System.out.println(path4.toString());
		// 得到绝对路径
		Path absolutePath = path4.toAbsolutePath();
		System.out.println(absolutePath.toString());
		// 得到当前运行的路径
		String property = System.getProperty("user.dir");
		Path path5 = Paths.get(property);
		System.out.println(property);
		// 得到真正路径
		Path normalize = Paths.get("E:\\360Downloads\\Apk\\tre\\1.txt")
				.normalize();
		System.out.println(normalize.toString());
		// 获取两个路径之间的距离
		Path relativize = normalize.relativize(path2);
		System.out.println(relativize.toString());
		// 获得修改时间
		try {
			FileTime lastModifiedTime = Files.getLastModifiedTime(path2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 遍历某个文件夹下面的所有文件 不会迭代遍历子文件夹的文件
	 */
	@Test
	public void testDirectory() {
		Path path2 = Paths.get(ParentPath);
		try {
			DirectoryStream<Path> newDirectoryStream = Files
					.newDirectoryStream(path2, x -> {
						x.toString().endsWith(".txt");
						return true;
					});
			for (Path p : newDirectoryStream) {
				System.out.println(p.getFileName());
				System.out.println(p.toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 遍历一个文件夹下的所有文件夹,不同的是 这里需要实现FileVisitor 我们可以继承SimpleFileVisitor
	 * 实现visitFile即可
	 */
	@Test
	public void testDirectoryTree() {
		Path path2 = Paths.get(ParentPath);
		try {
			Path walkFileTree = Files
					.walkFileTree(path2, new findJavaVisitor());
			System.out.println(walkFileTree.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static class findJavaVisitor extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			if (file.toString().endsWith(".txt")) {
				System.out.println(file.toString());
			}
			// TODO Auto-generated method stub
			return FileVisitResult.CONTINUE;
		}
	}

	@Test
	public void testAddAndDelete() {
		try {
			Path path2 = Paths.get(ParentPath + "\\crea22te.txt222");
			// 只能在linux windows不能
			Set<PosixFilePermission> fromString = PosixFilePermissions
					.fromString("rw-rw-rw-");
			FileAttribute<Set<PosixFilePermission>> asFileAttribute = PosixFilePermissions
					.asFileAttribute(fromString);

			Path createFile = Files.createFile(path2, asFileAttribute);
			System.out.println(createFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// file.copy(oldpath,newpath)
	// file.move(source,target,REPLACE_EXSITING,COPY_ATTRIBUTES)

	/**
	 * 读写文件的优化
	 */
	@Test
	public void testReadAndWriter() {
		// StandardCharsets.UTF_8
		Path path2 = Paths.get(PATH);
		String string = ParentPath + "\\writer.txt";
		Path path3 = Paths.get(string);
		try {
			Files.createFile(path3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (BufferedReader reader = Files.newBufferedReader(path2,
				Charset.forName("GBK"));
				BufferedWriter bw = Files.newBufferedWriter(path3,
						Charset.forName("UTF-8"), StandardOpenOption.WRITE)) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				bw.write(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 监听目录
	 */
	@Test
	public void testWatchFile() {
		// 监听目录变化
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = FileSystems.getDefault().getPath(ParentPath);
			WatchKey key = dir.register(watcher,
					StandardWatchEventKinds.ENTRY_MODIFY);
			boolean shutdown = false;
			while (!shutdown) {
				System.out.println(1);
				key = watcher.take();
				for (WatchEvent<?> event : key.pollEvents()) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
						System.out.println("Home dir changed!");
					}
				}
				key.reset();
			}
		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * seekableByteChannel 可以更灵活的操作苦文件的任何位置
	 */
	@Test
	public void testSeek() {
		Path path2 = Paths.get(PATH);
		try {
			FileChannel channel = FileChannel.open(path2,
					StandardOpenOption.READ);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int read = channel.read(buffer, channel.size() - 1000);
			System.out.println(read);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 通过将来式一度读取文件
	 */
	@Test
	public void testAsynchronousFileChannel(){
		Path path = Paths.get(ParentPath+"\\win7.iso");
		try {
			AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
			ByteBuffer buffer = ByteBuffer.allocate(1000000000);
			Future<Integer> read = channel.read(buffer, 0);
			while(!read.isDone()){
				System.out.println("read");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Integer resoult = read.get();
				System.out.println(resoult);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 异步读写  的回调式
	 * @throws IOException 
	 */
	@Test
	public void testAsynChronous2(){
		Path path = Paths.get(ParentPath+"\\win7.iso");
		AsynchronousFileChannel channel;
		try {
			channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
			ByteBuffer buffer = ByteBuffer.allocate(100000); //这里要读完才会出来 数组 太大的话就不会触发了
			channel.read(buffer, 0,buffer,new CompletionHandler<Integer, ByteBuffer>() {

				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					System.out.println("1");
					System.out.println("completed");
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					System.out.println("failed");
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("go");
	}
	
	/**
	 * networkchannel 
	 */
	@Test
	public void testNetWorkChannel(){
		 SelectorProvider provider = SelectorProvider.provider();
		 System.out.println("go");
		 System.out.println("go");
		 System.out.println("go");
		 
		 System.out.println("go");

		
	}
	
	
	
}
