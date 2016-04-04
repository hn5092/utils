package java7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.junit.Test;

public class TestNew {

  @Test
  public void test() {
    try {
      throw new InterruptedException();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 使用try-with-resources语法 但是在try中不要使用包装,分开来写
   */
  @Test
  public void testNewTry() {

    try (OutputStream out = new FileOutputStream(new File("e:"))) {

      byte[] buf = new byte[4096];

      int len;


    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * 监听文件的变化
   * 
   */
  @Test
  public void testWatchFile() {
    // 监听目录变化
    try {
      WatchService watcher = FileSystems.getDefault().newWatchService();

      Path dir = FileSystems.getDefault().getPath(PATH);

      WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

      boolean shutdown = false;
      while (!shutdown) {
        System.out.println(1);
        key = watcher.take();
        for (WatchEvent<?> event : key.pollEvents()) {
          if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
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
   * 遍历目录
   * 
   * @throws IOException
   */
  String PATH = "E:\\360Downloads";

  @Test
  public void testFileTree() throws IOException {
    Path startingDir = Paths.get(PATH);
    // 遍历目录
    Files.walkFileTree(startingDir, new FindJavaVisitor());
  }

  private static class FindJavaVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

      if (file.toString().endsWith(".txt")) {
        System.out.println(file.getFileName());
        System.out.println(file.toString());
      }
      return FileVisitResult.CONTINUE;
    }
  }

  @Test
  public void testPaths() {
    try {
      Path zip = Paths.get(PATH);
      // Path zip = Paths.get("E:\\java\\测试.rar");
      System.out.println(zip.toAbsolutePath().toString());
      System.out.println(Files.getLastModifiedTime(zip));
      System.out.println(Files.size(zip));
      System.out.println(Files.isSymbolicLink(zip));
      System.out.println(Files.isDirectory(zip));
      System.out.println(Files.readAttributes(zip, "*"));

    } catch (IOException ex) {
      System.out.println("Exception" + ex.getMessage());
    }
  }
  /**
   * jdk7 文件写操作
   */
  @Test
  public void testNewRead() {
    Path old = Paths.get(PATH);

    Path target = Paths.get("E:\\java\\test\\test1.txt");


    try (BufferedReader reader = Files.newBufferedReader(old, StandardCharsets.UTF_8)) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {

    }

    try (BufferedWriter writer =
        Files.newBufferedWriter(target, StandardCharsets.UTF_8, StandardOpenOption.WRITE)) {
      writer.write("hello");
    } catch (IOException e) {

      e.printStackTrace();
    }
    try {
      @SuppressWarnings("unused")
      List<String> lines = Files.readAllLines(old, StandardCharsets.UTF_8);
      @SuppressWarnings("unused")
      byte[] bytes = Files.readAllBytes(old);
    } catch (IOException e) {

      e.printStackTrace();
    }

  }
}
