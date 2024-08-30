package frc.robot.tuning;

import edu.wpi.first.wpilibj.Filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public final class RobotConfiguration {

    private static final RobotConfigurationParser parser;
    private static final String basePath;

    private static record RobotConfigurationIntSource(String name, int value) implements IntSource {}

    private static record RobotConfigurationFloatSource(String name, float value) implements FloatSource {}

    private static final Map<String, Float> values = new HashMap<>();


    static {
        parser = new RobotConfigurationParser(values);
        basePath = Filesystem.getDeployDirectory().getPath();
    }

    private RobotConfiguration() {}

    public static void addFile(String fileName) {
        try {
            String fileContents = new String(Files.readAllBytes(Paths.get(basePath, fileName)));
            parser.parse(fileContents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public IntSource getInt(String key) {
        if (!values.containsKey(key)) {
            throw new Error("Could not find config value for key " + key);
        }
        return new RobotConfigurationIntSource(key, (int) (float) values.get(key));
    }

    public FloatSource getFloat(String key) {
        if (!values.containsKey(key)) {
            throw new Error("Could not find config value for key " + key);
        }
        return new RobotConfigurationFloatSource(key, values.get(key));
    }


    private static class RobotConfigurationParser {
        private final Map<String, Float> values;
        private final Stack<String> currentNamespace = new Stack<>();


        public RobotConfigurationParser(Map<String, Float> values) {
            this.values = values;
        }

        public void parse(String string) {
            TokenStream tokens = tokenize(string);
            currentNamespace.clear();

            while (!tokens.isExhausted()) {
                String name = tokens.getNextMatching("[\\w-]+|\\}");
                if (name == null) break;

                if (name.equals("}")) {
                    currentNamespace.pop();
                    continue;
                }

                currentNamespace.push(name);

                String op = tokens.getNextMatching("[={]");
                if (op == null) break;

                if (op.equals("=")) {
                    String number = tokens.getNextMatching("-?(0|[1-9]\\d*)(\\.\\d+)?");
                    if (number != null) {
                        float value = Float.parseFloat(number);
                        values.put(String.join(".", currentNamespace), value);
                    }
                    currentNamespace.pop();
                }
            }
        }

        private TokenStream tokenize(String string) {
            TokenStream tokens = new TokenStream();
            for (String line : string.split("\\s*\n\\s*")) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) {
                    continue;
                }
                tokens.insert(line.split("\\s+"));
            }

            return tokens;
        }

        private static class TokenStream {
            private final Queue<String> tokenQueue = new LinkedList<>();

            public void insert(String... tokens) {
                tokenQueue.addAll(List.of(tokens));
            }

            public String getNextMatching(String regex) {
                String op;
                while ((op = tokenQueue.poll()) != null) {
                    if (op.matches("^" + regex + "$")) {
                        return op;
                    }

                    System.out.println("Invalid token while parsing config file: \"" + op + "\"");
                }
                return null;
            }

            public boolean isExhausted() {
                return tokenQueue.isEmpty();
            }
        }
    }
}