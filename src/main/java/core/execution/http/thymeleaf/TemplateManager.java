package core.execution.http.thymeleaf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateManager implements TemplateInitializer, TemplateFinder {

    public TemplateManager() {
    }

    @Override
    public void initialize() throws IOException {
        TemplatesContainer.init(findAndReadAllFiles());
    }

    private Map<String, Template> findAndReadAllFiles() throws IOException {
        File folder = new File("C:/JavaProjects/MyFramework/src/main/resources/templates");
        List<File> files = new ArrayList<>();
        listFilesForFolder(folder, files);
        Map<String, Template> templateMap = readFiles(files, folder.getPath());

        return templateMap;
    }

    private Map<String, Template> readFiles(List<File> files, String pathToRemove) throws IOException {
        Map<String, Template> templateMap = new HashMap<>();
        for (File file : files
        ) {
            String path = fixPath(file.getPath(), pathToRemove);
            String fileAsString = readFileAsString(file);
            Template template = new Template(fileAsString);
            templateMap.put(path, template);
        }
        return templateMap;
    }

    private String fixPath(String path, String pathToRemove) {
        return path.replace(pathToRemove, "").replace("\\", "/");

    }


    private String readFileAsString(File file) throws IOException {
        StringBuilder sb = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }


    private void listFilesForFolder(File folder, List<File> files) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, files);
            } else {
                files.add(fileEntry);
            }
        }
    }

    @Override
    public String findTemplate(String path) {
        return TemplatesContainer.getInstance().getTemplates(path);
    }
}
