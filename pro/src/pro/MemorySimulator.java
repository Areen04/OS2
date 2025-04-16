package pro;

import java.util.Scanner;

public class MemorySimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Ask user for number of memory blocks
        System.out.print("Enter the number of memory blocks: ");
        int M = scanner.nextInt();

        // 2. Create the memory array (the actual data structure)
        MemoryBlock[] memory = new MemoryBlock[M];

        int currentStart = 0;

        // 3. Loop to create each block and initialize it
        for (int i = 0; i < M; i++) {
            System.out.print("Enter size of block #" + (i + 1) + " in KB: ");
            int size = scanner.nextInt();

            memory[i] = new MemoryBlock(size, currentStart);
            currentStart = memory[i].endAddress + 1;
        }

        // 4. Ask user to choose allocation strategy
        System.out.print("\nEnter allocation strategy (1 = First-Fit, 2 = Best-Fit, 3 = Worst-Fit): ");
        int strategy = scanner.nextInt();

        boolean running = true;

        while (running) {
            // Display Menu
            System.out.println("\n============================================");
            System.out.println("1) Allocate memory for a process");
            System.out.println("2) Deallocate memory");
            System.out.println("3) Print memory status report");
            System.out.println("4) Exit");
            System.out.println("============================================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> {
                    // Allocation
                    System.out.print("Enter process ID: ");
                    String processID = scanner.nextLine();
                    System.out.print("Enter process size (KB): ");
                    int size = scanner.nextInt();

                    boolean allocated = false;
                    if (strategy == 1) {
                        allocated = allocateFirstFit(memory, processID, size);
                    } else if (strategy == 2) {
                        allocated = allocateBestFit(memory, processID, size);
                    } else if (strategy == 3) {
                        allocated = allocateWorstFit(memory, processID, size);
                    } else {
                        System.out.println("Invalid strategy!");
                    }

                    if (!allocated) {
                        System.out.println("Allocation failed: No suitable block available.");
                    }
                }
                case 2 -> {
                    // Deallocation
                    System.out.print("Enter process ID to deallocate: ");
                    String processID = scanner.nextLine();
                    boolean found = false;
                    for (MemoryBlock block : memory) {
                        if (block.processID.equals(processID)) {
                            block.status = "free";
                            block.processID = "null";
                            block.internalFragmentation = 0;
                            found = true;
                            System.out.println("Process " + processID + " has been deallocated.");
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Error: Process ID not found.");
                    }
                }
                case 3 -> {
                    // Print memory status
                    System.out.println("\nMemory Status Report:");
                    System.out.println("==========================================================");
                    System.out.println("Block#  Size  Start-End     Status     ProcessID  Fragmentation");
                    System.out.println("==========================================================");
                    for (int i = 0; i < memory.length; i++) {
                        MemoryBlock b = memory[i];
                        String frag = b.status.equals("allocated") ? String.valueOf(b.internalFragmentation) : "-";
                        System.out.printf("Block%-2d  %-4d  %-5d-%-5d  %-9s  %-9s  %s\n",
                                i, b.blockSize, b.startAddress, b.endAddress,
                                b.status, b.processID, frag);
                    }
                    System.out.println("==========================================================");
                }
                case 4 -> {
                    // Exit
                    running = false;
                    System.out.println("Simulation ended. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please enter 1-4.");
            }
        }

        scanner.close();
    }
 /**
     * Allocates memory using the First Fit strategy:
     * Finds the first free block that is large enough for the process.
     */
    public static boolean allocateFirstFit(MemoryBlock[] memory, String processID, int processSize) {
        for (MemoryBlock block : memory) {
            if (block.status.equals("free") && block.blockSize >= processSize) {
                block.status = "allocated";
                block.processID = processID;
                block.internalFragmentation = block.blockSize - processSize;
                System.out.println(processID + " Allocated at address " + block.startAddress +
                        ", and the internal fragmentation is " + block.internalFragmentation);
                return true;
            }
        }
        return false;
    }
/**
     * Allocates memory using the Best Fit strategy:
     * Finds the smallest free block that fits the process.
     */
    public static boolean allocateBestFit(MemoryBlock[] memory, String processID, int processSize) {
        MemoryBlock bestBlock = null;
        for (MemoryBlock block : memory) {
            if (block.status.equals("free") && block.blockSize >= processSize) {
                if (bestBlock == null || block.blockSize < bestBlock.blockSize) {
                    bestBlock = block;
                }
            }
        }
        if (bestBlock != null) {
            bestBlock.status = "allocated";
            bestBlock.processID = processID;
            bestBlock.internalFragmentation = bestBlock.blockSize - processSize;
            System.out.println(processID + " Allocated at address " + bestBlock.startAddress +
                    ", and the internal fragmentation is " + bestBlock.internalFragmentation);
            return true;
        }
        return false;
    }
 /**
     * Allocates memory using the Worst Fit strategy:
     * Finds the largest free block that fits the process.
     */
    public static boolean allocateWorstFit(MemoryBlock[] memory, String processID, int processSize) {
        MemoryBlock worstBlock = null;
        for (MemoryBlock block : memory) {
            if (block.status.equals("free") && block.blockSize >= processSize) {
                if (worstBlock == null || block.blockSize > worstBlock.blockSize) {
                    worstBlock = block;
                }
            }
        }
        if (worstBlock != null) {
            worstBlock.status = "allocated";
            worstBlock.processID = processID;
            worstBlock.internalFragmentation = worstBlock.blockSize - processSize;
            System.out.println(processID + " Allocated at address " + worstBlock.startAddress +
                    ", and the internal fragmentation is " + worstBlock.internalFragmentation);
            return true;
        }
        return false;
    }
}
