import java.io.*;
import java.util.Arrays;

public class Problem1 {
    public static void main(String[] args) {
        // Files that contains input
//        File inputSmall = new File("D:\\raoinfotech\\src\\TWSP_small.txt");
        File inputLarge = new File("D:\\raoinfotech\\src\\TWSP_large.txt");

        // Files that'll get output for both datasets
//        File outputSmall = new File("D:\\raoinfotech\\src\\P1OutputSmall.txt");
        File outputLarge = new File("D:\\raoinfotech\\src\\P1OutputLarge.txt");

        // For small dataset
//        solveProblem(inputSmall, outputSmall);
        // And for large dataset
        solveProblem(inputLarge, outputLarge);
    }

    static class HomePage {
        int textContent, images, forms;

        public HomePage(int textContent, int images, int forms) {
            this.textContent = textContent;
            this.images = images;
            this.forms = forms;
        }
    }

    static void solveProblem(File inputFile, File outputFile) {
        HomePage[] sites;
        int index = 0;
        try {
            // BufferedReader to read lines from the file
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            // To get the first line that contains number of testcases
            String line = br.readLine();
            int numTestCases = Integer.parseInt(line);
            sites = new HomePage[numTestCases];

            // Now check all the testcases and put them in array
            for (int i = 0; i < numTestCases; i++) {
                line = br.readLine();
                // Split them ignoring spaces
                line = line.trim();
                String[] nums = line.split("\\s+");
                System.out.println(Arrays.toString(nums) + "->" + index);
                int a = Integer.parseInt(nums[0]);
                int b = Integer.parseInt(nums[1]);
                int c = Integer.parseInt(nums[2]);

                // Put the homepage data in array
                sites[index] = new HomePage(a, b, c);
                index++;
            }

            // Now we've out input array ready so pass it to the function
            webServerProblem(sites);

            // Now write the output to the file
            // For small dataset
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
                for (HomePage hp: sites) {
                    System.out.println(hp.textContent + ", " + hp.images + ", " + hp.forms);
                    bw.write(hp.textContent + ", " + hp.images + ", " + hp.forms);
                    bw.newLine();
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void webServerProblem(HomePage[] sites) {
        // We'll sort the HomePage array in order that smallest sized text content website loads first and
        // Similar for other to factors, images and forms
        Arrays.sort(sites, (a, b) -> {
            if (a.textContent == b.textContent) {
                // If for both pages the textContent is of same size then we'll check for images and forms
                if (a.images == b.images)
                    // For the forms it's not specified in the question if more forms are good or less
                    // So I assume that fewer forms are preferred
                    return a.forms - b.forms;
                else
                    // Here we need more images content
                    return b.images - a.images;
            }else if (a.images == b.images) {
                return a.forms - b.forms;
            }else {
                return a.textContent - b.textContent;
            }
        });
    }
}
