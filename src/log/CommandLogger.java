package src.log;

import src.command.commandImpl.AbstractCommand;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CommandLogger implements Observer {
    private List<AbstractCommand> executedCommands = new ArrayList<>();

    @Override
    public void update(AbstractCommand executedCommand) {
        executedCommands.add(executedCommand);
        saveToLogFile(executedCommand);
    }

    private void saveToLogFile(AbstractCommand command) {
        try (FileWriter fileWriter = new FileWriter(".log", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(command.ctx.getDate() +" "+ command.getOriginCommand());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logCommands() {
        System.out.println("Executed Commands:");
        int length = executedCommands.size();
        for(int i = length - 1; i >= 0; i--) {
            System.out.println(executedCommands.get(i).getOriginCommand());
        }
    }
    public void logCommands(int n) {
        System.out.println("Executed Commands:");
        int length = executedCommands.size();
        if(n > length) logCommands();
        for(int i = length - 1; i >= length - n; i--) {
            System.out.println(executedCommands.get(i).getOriginCommand());
        }
    }
}