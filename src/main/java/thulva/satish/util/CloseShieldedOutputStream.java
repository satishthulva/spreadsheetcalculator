package thulva.satish.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author satish.thulva. Generated on 08/11/18
 **/
public class CloseShieldedOutputStream extends OutputStream {
    private OutputStream outputStream;

    public CloseShieldedOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void close() {}
}
