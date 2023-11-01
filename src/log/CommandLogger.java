package src.log;
import java.io.BufferedReader;
import java.io.FileReader;
import src.command.commandImpl.AbstractCommand;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static src.utils.TimeUtils.FormattedTime;

public class CommandLogger implements Observer {

    private final List<AbstractCommand> executedCommands = new ArrayList<>();
    private final List<String> Lines = new ArrayList<>();

    public void ReadCommand() {
        try (BufferedReader br = new BufferedReader(new FileReader(".log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("session start")) {
                    Lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("日志记录失败:" + e.getMessage());
        }
    }
    @Override
    public void update(AbstractCommand executedCommand) {
        executedCommands.add(executedCommand);
        saveToLogFile(executedCommand);
    }

    private void saveToLogFile(AbstractCommand command) {
        try (FileWriter fileWriter = new FileWriter(".log", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(FormattedTime() +" "+ command.getOriginCommand());
        } catch (IOException e) {
            System.out.println("日志记录失败:" + e.getMessage());
        }
    }

    public void logCommands() {
        ReadCommand();
        System.out.println("Executed Commands:");
        int length = Lines.size();
        for(int i = length - 1; i >= 0; i--) {
            System.out.println(Lines.get(i));
        }
    }
    public void logCommands(int n) {
        ReadCommand();
        System.out.println("Executed Commands:");
        int length = Lines.size();
        if(n > length) logCommands();
        for(int i = length - 1; i >= length - n; i--) {
            System.out.println(Lines.get(i));
        }
    }
}
