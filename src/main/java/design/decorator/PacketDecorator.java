package design.decorator;

public abstract class PacketDecorator implements IPacketCreator {
	IPacketCreator component;

	public PacketDecorator(IPacketCreator c) {
		component = c;
	}

}
