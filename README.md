# ConfigFileReader
This is a program that can help other Java programs to read and write Configuration files.
## How to use it
Import the ConfigFileReader.jar package into your project.
Create a class and implement MyConfigFile interface. Write all Key-Value pairs and their default value into that class.

eg:
```

public class TryConfigFile implements MyConfigFile {

    private String document_root = "/var/try";

    private String timeout = "100ms";

}

```

When you want to read or write configuration file, use the ConfigFileManager class.

eg:
```

ConfigFileManager configFileManager = new ConfigFileManager(new TryConfigFile(),"/var/try",true);
String document_root = (String)configFileManager.get("document_root");
configFileManager.set("document_root","/aaa");
configFileManager.write();

```

Remember to write the configuration file after setting it.
