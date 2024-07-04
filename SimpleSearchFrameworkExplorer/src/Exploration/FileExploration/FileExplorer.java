package Exploration.FileExploration;

import Exploration.Document;
import Exploration.ExplorationContext;
import Exploration.ExplorationStopped;
import Exploration.Explorer;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.UUID;

public class FileExplorer {
    private UUID id = Explorer.newId();
    private String name = "File explorer";
    private Path path = FileSystemView.getFileSystemView().getHomeDirectory().toPath();
    private boolean exploresHiddenFiles = false;
    private boolean exploresDirectories = true;
    private boolean includeByteData = true;
    private long maximumByteSize = 1024 * 1024; // 1MiB
    private boolean excludeLargeFiles = true;

    public void explore(ExplorationContext context) {
        var maxDepth = exploresDirectories ? Integer.MAX_VALUE : 0;
        try (var files = Files.walk(path, maxDepth)) {
            files.filter(Files::isRegularFile)
                    .forEach(p -> find(context, p));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void find(
            ExplorationContext context,
            Path path) {
        if (context.stopped())
            throw new ExplorationStopped();
        try {
            var file = path.toFile();
            var size = file.length();
            if (size > maximumByteSize && excludeLargeFiles)
                return;

            var document = new Document(path.toString());
            document.add("name", file.getName());
            document.add("size", Long.toString(size));

            var basicAttributes = Files.readAttributes(path, BasicFileAttributes.class);
            document.add("creation_time", basicAttributes.creationTime().toString());
            document.add("lastAccess_time", basicAttributes.lastAccessTime().toString());
            document.add("lastModified_time", basicAttributes.lastModifiedTime().toString());
            // document.versionField("lastModified_time"); TODO: Implement proper version check so this makes sense
            if (context.shouldSkip(document))
                return;

            if (includeByteData && size <= maximumByteSize)
                document.add("bytes", Files.readAllBytes(path));

            context.add(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UUID id() {
        return id;
    }
    public void id(UUID id) {
        this.id = id;
    }

    public String name() {
        return name;
    }
    public void name(String name) {
        this.name = name;
    }

    public Path path() {
        return path;
    }

    public void path(Path path) {
        this.path = path;
    }

    public boolean exploresHiddenFiles() {
        return exploresHiddenFiles;
    }

    public void exploresHiddenFiles(boolean exploresHiddenFiles) {
        this.exploresHiddenFiles = exploresHiddenFiles;
    }

    public boolean exploresDirectories() {
        return exploresDirectories;
    }

    public void exploresDirectories(boolean exploresDirectories){
        this.exploresDirectories = exploresDirectories;
    }
}
