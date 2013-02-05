package Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Jama.Matrix;

public class MatrixUtils {
	private static final Logger logger = Logger.getLogger(MatrixUtils.class
			.getClass());

	public synchronized static Matrix fromFile(String filename) {
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			dom = db.parse(MatrixUtils.class.getClassLoader()
					.getResourceAsStream(filename));

			int qubits, syssize, Row, Col;

			Matrix toReturn = null;
			// get the root elememt
			Element docEle = dom.getDocumentElement();

			qubits = Integer.parseInt(docEle.getAttribute("NumQubits"));
			syssize = (int) Math.pow(2, qubits);
			toReturn = new Matrix(syssize, syssize);
			// get a nodelist of <MatrixElement> elements
			NodeList tcnl = docEle.getElementsByTagName("MatrixElement");
			if ((tcnl != null) && (tcnl.getLength() > 0)) {
				for (int i = 0; i < tcnl.getLength(); i++) {
					Element mel = (Element) tcnl.item(i);
					Row = Integer.parseInt(mel.getAttribute("Row"));
					Col = Integer.parseInt(mel.getAttribute("Column"));
					NodeList rnl = mel.getElementsByTagName("Real");
					NodeList inl = mel.getElementsByTagName("Imag");

					String textVal = null;
					Element el = (Element) rnl.item(0);
					textVal = el.getFirstChild().getNodeValue();
					double real = Double.parseDouble(textVal);

					el = (Element) inl.item(0);
					textVal = el.getFirstChild().getNodeValue();
					double complex = Double.parseDouble(textVal);

					toReturn.set(Row, Col, new Complex(real, complex));
				}
			}
			return toReturn;

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	public static void main(String args[]) {
		Matrix a = Matrix.identity(2, 2);
		toFile(a, "test.xml");
		Matrix b = fromFile("test.xml");
		b.printState();
	}

	public synchronized static Matrix tensor_prod(Matrix A, Matrix B) {
		Matrix to_return = new Matrix(
				(A.getRowDimension() * B.getRowDimension()),
				(A.getColumnDimension() * B.getColumnDimension()));

		Complex scalar_factor;
		Matrix resulting_matrix;

		int i0;
		int i1;
		int j0;
		int j1;

		for (int a_row = 0; a_row < A.getRowDimension(); a_row++) {
			i0 = a_row * B.getRowDimension();
			i1 = (a_row * B.getRowDimension()) + (B.getRowDimension() - 1);
			for (int a_col = 0; a_col < A.getColumnDimension(); a_col++) {
				scalar_factor = A.get(a_row, a_col);
				resulting_matrix = B.times(scalar_factor);
				j0 = a_col * B.getColumnDimension();
				j1 = (a_col * B.getColumnDimension())
						+ (B.getColumnDimension() - 1);

				to_return.setMatrix(i0, i1, j0, j1, resulting_matrix);
			}
		}

		return to_return;
	}

	public synchronized static void toFile(Matrix m, String filename) {
		if (!filename.endsWith(".xml")) {
			filename = filename.concat(".xml");
		}
		try {
			double qubits = Math.log(m.getRowDimension()) / Math.log(2);
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			out.write("<Matrix xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"testset_xml_spec.xsd\" NumQubits=\""
					+ Integer.toString((int) qubits) + "\">\n");
			for (int r = 0; r < m.getRowDimension(); r++) {
				for (int c = 0; c < m.getColumnDimension(); c++) {
					Complex e = m.get(r, c);
					if (!(((Math.abs(e.real())) < 0.000001)

					&& ((Math.abs(e.imag())) < 0.000001))) {
						out.write("<MatrixElement Row=\"" + Integer.toString(r)
								+ "\" Column=\"" + Integer.toString(c)
								+ "\">\n");
						out.write("<Real>" + e.real() + "</Real>\n");
						out.write("<Imag>" + e.imag() + "</Imag>\n");
						out.write("</MatrixElement>\n");
					}
				}
			}
			out.write("</Matrix>\n");
			out.close();
		} catch (IOException e) {
			logger.error(
					"IOException caught when trying to write matrix to file : "
							+ filename, e);
		}

	}
}
