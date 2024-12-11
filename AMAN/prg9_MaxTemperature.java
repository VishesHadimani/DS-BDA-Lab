
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.SparkConf;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class MaxTemperature {
    public static void main(String[] args) {
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
        SparkConf conf = new SparkConf().setAppName("Max Temperature").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        String inputFile = args[0];
        JavaRDD<String> lines = sc.textFile(inputFile);

        JavaRDD<Double> temperatures = lines.map(new Function<String, Double>() {
            @Override
            public Double call(String line) {
                String[] parts = line.split(",");
                return Double.parseDouble(parts[1]);
            }
        });

        Double maxTemperature = temperatures.reduce((a, b) -> Math.max(a, b));
        System.out.println("Maximum Temperature: " + maxTemperature);

        sc.stop();
    }
}
