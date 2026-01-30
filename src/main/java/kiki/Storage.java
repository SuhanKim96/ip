package kiki;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Storage class that handles the loading and saving of task data.
 */
public class Storage {
    private static final String FILE_PATH = "./data/kiki.txt";

    /**
     * Saves the current list of tasks to the hard disk.
     *
     * @param tasks The ArrayList of tasks to save.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File f = new File(FILE_PATH);
            f.getParentFile().mkdirs();

            FileWriter fw = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                fw.write(task.toFileString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the hard disk.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws KikiException If the file format is corrupted.
     */
    public ArrayList<Task> load() throws KikiException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File f = new File(FILE_PATH);

        if (!f.exists()) {
            return loadedTasks;
        }

        try {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] parts = line.split(" \\| ");

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                Task task = null;
                switch (type) {
                    case "T":
                        task = new Todo(description);
                        break;
                    case "D":
                        task = new Deadline(description, parts[3]);
                        break;
                    case "E":
                        task = new Event(description, parts[3], parts[4]);
                        break;
                    default:
                        throw new KikiException("Unknown task type in file.");
                }

                if (isDone) {
                    task.markAsDone();
                }
                loadedTasks.add(task);
            }
        } catch (Exception e) {
            throw new KikiException("Error loading file data: " + e.getMessage());
        }
        return loadedTasks;
    }
}