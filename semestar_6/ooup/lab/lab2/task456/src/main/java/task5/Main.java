package task5;

import java.io.IOException;

import task5.listeners.AveragePrintListener;
import task5.listeners.FileWriteListener;
import task5.listeners.MedianPrintListener;
import task5.listeners.SumPrintListener;
import task5.sources.DatotecniIzvor;

public class Main {

	public static void main(String[] args) {
		
		String inputData = "in.txt";
		String outData = "out.txt";
		
		try (IzvorBrojeva source = new DatotecniIzvor(inputData)) {
			SlijedBrojeva sb = new SlijedBrojeva(source);
			sb.addCollectionListener(new AveragePrintListener());
			sb.addCollectionListener(new MedianPrintListener());
			sb.addCollectionListener(new SumPrintListener());
			sb.addCollectionListener(new FileWriteListener(outData));
			sb.kreni();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
