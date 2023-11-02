package log;

import command.commandImpl.AbstractCommand;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    // 添加观察者列表
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }


    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // 通知观察者
    public void notifyObservers(AbstractCommand command) {
        for (Observer observer : observers) {
            observer.update(command);
        }
    }
}
