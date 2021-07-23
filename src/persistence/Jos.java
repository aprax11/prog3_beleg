package persistence;

import automat.GeschäftslogikImpl;

import javax.imageio.IIOException;
import java.io.*;

public class Jos {

    public static void serialize(String filename, GeschäftslogikImpl gl) {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            write(oos, gl);
        }catch (Exception ignored) {
        }
    }
    public static void write(ObjectOutputStream oos, GeschäftslogikImpl gl) {
        try {
            oos.writeObject(gl);
        } catch (IOException ignored) {
        }
    }
    public static GeschäftslogikImpl deserialize(String filename) {
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            return read(ois);
        }catch(Exception ignored) {
        }
        return null;
    }
    public static GeschäftslogikImpl read(ObjectInput objectInput) {
        try {
            return (GeschäftslogikImpl) objectInput.readObject();
        } catch (ClassNotFoundException ignored) {
        } catch (IOException ignored) {
        }
        return null;
    }
}
