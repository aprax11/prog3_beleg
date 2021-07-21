package persistence;

import automat.GeschäftslogikImpl;
import org.junit.jupiter.api.Test;
import persistence.Jos;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JosTest {
    @Test
    public void writeTest() throws IOException {
        ObjectOutputStream mockOos = mock(ObjectOutputStream.class);
        GeschäftslogikImpl mockGl = mock(GeschäftslogikImpl.class);

        Jos.write(mockOos, mockGl);
        verify(mockOos).writeObject(mockGl);
    }

    @Test
    public void readTest() throws IOException, ClassNotFoundException {
        ObjectInput mockIn= mock(ObjectInput.class);

        Jos.read(mockIn);
        verify(mockIn).readObject();
    }

}