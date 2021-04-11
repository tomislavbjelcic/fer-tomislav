package task5;

import java.io.Closeable;
import java.io.IOException;

public interface IzvorBrojeva extends Closeable {
	
	int getNext() throws IOException;
	
}
