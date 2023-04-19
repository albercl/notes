# Exportación de archivos excel

## Anotación para marcar columnas

```Java
public @interface ExcelColumn {
 String value();
}

public class MyDTO {
    @ExcelColumn("entity.id")
    private int id;
    @ExcelColumn("entity.name")
    private String name
    private String password;

    /* 
    Getters and setters
    ... 
    */
}
```

## Excel parser

```Java
public class ExcelParser<T> {
    public void generarExcel(List<T> items, Class<T> clazz) {
        ...
    }

    public void escribirExcel(OutputStream os) throws IOException {
        ...
    }

    public static void main(String[] args) {
        ExcelParser<MyDTO> parser = new ExcelParser<>();
        parser.generarExcel(Arrays.asList(new MyDTO(1, "alber"), new MyDTO(2, "test")));
        // Escribe excel solo para las columnas con la anotación
        parser.escribirExcel(myOs);
    }
}
```
