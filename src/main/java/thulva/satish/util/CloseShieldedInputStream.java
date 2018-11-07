package thulva.satish.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author satish.thulva. Generated on 08/11/18
 **/
public class CloseShieldedInputStream extends InputStream {

    private InputStream inputStream;

    public CloseShieldedInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public void close() {}
}
