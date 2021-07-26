import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class CalculateRewards {
        
        static class CustReward {
                String name;
                int rewards[];
                
                public CustReward(String name) {
                        this.name = name;
                        this.rewards = new int[13]; // index 0 is dummy
                }
                
                void addReward(int month, int reward) {
                        rewards[month] = reward;
                }

                @Override
                public String toString() {
                        String s = "Customer: " + name + "\n";
                        for(int i=1; i<=12; i++) {
                                s += "Month " + i + ": Rewards " + rewards[i] + "\n";
                        }
                        return s;
                }
                
        }
        
        public static int getRewards(int txnAmount) {
                if(txnAmount <= 50) {
                        return 0;
                }
                if(txnAmount <= 100) {
                        return txnAmount - 50;
                }
                return (txnAmount - 100) * 2 + 50;
        }

        public static void main(String[] args) {
                
                // Lets say, dataset is stored in file: rewards.txt
                String fileName = "rewards.txt";
                
                HashMap<String, CustReward> customerRewards = new HashMap<>();
                
                try {
                        Scanner reader = new Scanner(new File(fileName));
                        
                        while(reader.hasNextLine()) {
                                String line = reader.nextLine();
                                // Line has format: Name, Txn Amount, YYYY-MM-DD
                                
                                // Split string into tokens.
                                String tokens[] = line.split(",");
                                String custName = tokens[0];
                                int saleAmount = Integer.parseInt(tokens[1]);
                                int reward = getRewards(saleAmount);
                                int month = Integer.parseInt(tokens[2].split("-")[1]);
                                
                                if(customerRewards.containsKey(custName)) {
                                        customerRewards.get(custName).addReward(month, reward);
                                } else {
                                        CustReward c = new CustReward(custName);
                                        c.addReward(month, reward);
                                        customerRewards.put(custName, c);
                                }
                        }
                        
                        reader.close();
                        
                        // Now print for all customers.
                        for(CustReward c: customerRewards.values()) {
                                System.out.println(c);
                        }
                        
                } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                
                
                
                
        }

}