import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.faces.application.FacesMessage;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelParser<T> {

	private final Workbook wb = new HSSFWorkbook();

	private static final short DATE_FORMAT = 14;
	private static final short DOUBLE_FORMAT = 2;
	private static final short INTEGER_FORMAT = 1;

	public void generarExcel( List<T> items, Class<T> clazz )
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		ResourceBundle messages = ResourceBundle.getBundle( "messages" );

		Sheet sheet = wb.createSheet( "Propuestas" );
		Font font = wb.createFont();
		font.setBold( true );
		CellStyle headerCs = wb.createCellStyle();
		headerCs.setFont( font );

		List<Field> fields = Arrays.stream( clazz.getDeclaredFields() )
				.filter( field -> field.getAnnotation( ExcelColumn.class ) != null ).collect( Collectors.toList() );

		if ( fields.isEmpty() ) {
			FacesContext.instance().addMessage( null,
					new FacesMessage( FacesMessage.SEVERITY_ERROR, "No se han encontrado campos para exportar",
							"No se han encontrado campos para exportar" ) );
			return;
		}

		Row headerRow = sheet.createRow( 0 );

		Row[] rows = new Row[items.size()];
		for ( int i = 0; i < items.size(); i++ ) {
			rows[i] = sheet.createRow( i + 1 );
		}

		for ( int i = 0; i < fields.size(); i++ ) {
			final Field field = fields.get( i );
			Cell cell = headerRow.createCell( i );
			ExcelColumn excelColumn = field.getAnnotation( ExcelColumn.class );
			String key = excelColumn.value();

			if ( messages.containsKey( key ) )
				cell.setCellValue( messages.getString( key ) );
			else
				cell.setCellValue( key );

			cell.setCellStyle( headerCs );

			for ( int j = 0; j < items.size(); j++ ) {
				final int finalI = i;
				final T item = items.get( j );
				final Row row = rows[j];

				String prefix = field.getType().equals( Boolean.class ) ? "is" : "get";
				String getterName =
						prefix + field.getName().substring( 0, 1 ).toUpperCase() + field.getName().substring( 1 );

				Optional.ofNullable( clazz.getMethod( getterName ).invoke( item ) ).ifPresent( value -> {
					Cell c = row.createCell( finalI );
					c.setCellStyle( createCellStyle( field ) );
					setCellValue( field, c, value );
				} );
			}

			sheet.autoSizeColumn( i );
		}
	}

	public void escribirExcel( OutputStream os ) throws IOException {
		wb.write( os );
	}

	private CellStyle createCellStyle( Field field ) {
		CellStyle cs = wb.createCellStyle();

		if ( TypeUtils.isDate( field.getType() ) )
			cs.setDataFormat( DATE_FORMAT );
		else if ( TypeUtils.isFloat( field.getType() ) )
			cs.setDataFormat( DOUBLE_FORMAT );
		else if ( TypeUtils.isInteger( field.getType() ) )
			cs.setDataFormat( INTEGER_FORMAT );

		return cs;
	}

	private void setCellValue( Field field, Cell cell, Object value ) {
		if ( TypeUtils.isDate( field.getType() ) ) {
			cell.setCellValue( ( Date ) value );
		} else if ( TypeUtils.isNumeric( field.getType() ) ) {
			cell.setCellValue( Double.parseDouble( value.toString() ) );
		} else if ( TypeUtils.isBoolean( field.getType() ) ) {
			cell.setCellValue( ( Boolean ) value );
		} else {
			cell.setCellValue( value.toString() );
		}
	}
}
