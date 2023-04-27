# Internacionalización JBoss y SEAM

## Inyección de dependencias

```Java
@In( value = "messages")
private Map<String, String> messages;
```

## En línea

```Java
import org.jboss.seam.core.SeamResourceBundle;

ResourceBundle messages = SeamResourceBundle.getBundleNamed( "messages" );
```

```Java
import org.jboss.seam.international.Messages;

Map<String, String> messages = Messages.instance();
```
