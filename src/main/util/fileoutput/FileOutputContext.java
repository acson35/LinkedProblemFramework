package main.util.fileoutput;

import java.io.BufferedWriter;
import java.io.Serializable;

public interface FileOutputContext extends Serializable {
	  BufferedWriter getFileWriter();

	  String getSeparator();

	  void setSeparator(String separator);

	  String getFileName();
}
