package net.mindustry_ddns.filestore.old;

import net.mindustry_ddns.filestore.FileStore;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;


/**
 * Base class for specific implementations of a file store.
 *
 * @param <T> the stored object type
 */
public abstract class AbstractFileStore<T> implements FileStore<T> {

    private File file;
    private T object;

    protected AbstractFileStore(File file, Supplier<T> supplier) {
        this.file = file;
        this.object = supplier.get();
    }

    protected AbstractFileStore(String path, Supplier<T> supplier) {
        this(new File(path), supplier);
    }

    protected abstract void saveImpl() throws IOException;

    protected abstract void loadImpl() throws IOException;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void save() {
        getFile().getAbsoluteFile().getParentFile().mkdirs();

        try {
            saveImpl();
        } catch (IOException e) {
            throw new RuntimeException("Unable to save the file store at " + getFile(), e);
        }
    }

    @Override
    public void load() {
        if (!getFile().exists()) {
            save();
        } else {
            try {
                loadImpl();
            } catch (IOException e) {
                throw new RuntimeException("Unable to load the file store at " + getFile(), e);
            }
        }
    }

    @Override
    public T get() {
        return object;
    }

    @Override
    public void set(T object) {
        this.object = object;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(String path) {
        this.file = new File(path);
    }

    @Override
    public String toString() {
        return object.toString();
    }
}
