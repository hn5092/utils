package design.observer;

public interface ISubject {
  public void attach(IObserver observer);
  public void inform(int state);
}
