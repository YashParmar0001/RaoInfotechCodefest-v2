package Problem2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Problem2 {
    public static void main(String[] args) {
        File inputSmall = new File("D:\\raoinfotech\\src\\Problem2\\ET_small.txt");
        File inputLarge = new File("D:\\raoinfotech\\src\\Problem2\\ET_large.txt");

        File outputSmall = new File("D:\\raoinfotech\\src\\Problem2\\P2OutputSmall.txt");
        File outputLarge = new File("D:\\raoinfotech\\src\\Problem2\\P2OutputLarge.txt");

        europeonTownship(inputSmall, outputSmall);
        europeonTownship(inputLarge, outputLarge);
    }

    static void europeonTownship(File inputFile, File outputFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();
            int testCases = Integer.parseInt(line);
            List<House[]> townships = new ArrayList<>();

            for (int i = 0; i < testCases; i++) {
                line = br.readLine();
                int noOfHouse = Integer.parseInt(line);
                House[] houses = new House[noOfHouse];
                int index = 0;
                for (int j = 0; j < noOfHouse; j++) {
                    line = br.readLine();
                    String[] data = line.split(",");
                    int totalRooms = Integer.parseInt(data[0]);
                    int roofRooms = Integer.parseInt(data[1]);
                    int standardRooms = Integer.parseInt(data[2]);
                    int halls = Integer.parseInt(data[3]);
                    houses[index] = new House(totalRooms, roofRooms, standardRooms, halls);
                    index++;
                }
                townships.add(houses);
            }

            // Now we've all the inputs
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
                PrintWriter pw = new PrintWriter(bw);
                int caseCount = 1;
                for (House[] township: townships) {
                    double[] ans = calculateForTownship(township);
                    pw.printf("Case #%d: %.2f, %.2f, %.2f\n", caseCount++, ans[0], ans[1], ans[2]);
                }
                bw.close();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    static double[] calculateForTownship(House[] township) {
        double time = 0, quantityAcc = 0, quantityReg = 0;
        for (House house: township) {
            double[] data = calculate(house);
            time += data[0];
            quantityAcc += data[1];
            quantityReg += data[2];
        }
        return new double[]{time, quantityAcc, quantityReg};
    }

    static double[] calculate(House house) {
        double quantityAcc = 0, quantityReg = 0, timeAcc = 0, timeReg = 0;
        // Let's calculate for the
        double roofRoomAccPart = (double) (house.roofRooms*3)/3;
        double standardRoomAccPart = (double) (house.standardRooms*4)/3;
        double hallsAccPart = (double) (house.halls*6)/3;

        double roofRoomRegPart = (house.roofRooms*3) - roofRoomAccPart;
        double standardRoomRegPart = (house.standardRooms*4) - standardRoomAccPart;
        double hallsRegPart = (house.halls*6) - hallsAccPart;

        quantityAcc += (roofRoomAccPart + standardRoomAccPart + hallsAccPart) * 1.5;
        quantityReg += (standardRoomRegPart + roofRoomRegPart + hallsRegPart) * 2.25;

        timeAcc += (2.5 * quantityAcc) / 1.5;
        timeReg += (3.25 * quantityReg) / 2.25;

        double totalTime = timeAcc + timeReg;

        return new double[]{totalTime, quantityAcc, quantityReg};
    }

    static class House {
        int totalRooms, roofRooms, standardRooms, halls;
        public House(int totalRooms, int roofRooms, int standardRooms, int halls) {
            this.totalRooms = totalRooms;
            this.roofRooms = roofRooms;
            this.standardRooms = standardRooms;
            this.halls = halls;
        }
    }
}
