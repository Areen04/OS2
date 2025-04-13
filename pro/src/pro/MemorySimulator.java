/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

            // Create the block and assign it to the array
            memory[i] = new MemoryBlock(size, currentStart);

            // Update starting point for next block
            currentStart = memory[i].endAddress + 1;
        }

        // 4. Print the initialized memory
        System.out.println("\nMemory Blocks Initialized:");
        System.out.println("Block#  Size(KB)  Start-End  Status  ProcessID  Fragmentation");

        for (int i = 0; i < M; i++) {
            MemoryBlock block = memory[i];
            System.out.println("Block" + i + "   "
                    + block.blockSize + "KB   "
                    + block.startAddress + "-" + block.endAddress + "   "
                    + block.status + "   "
                    + block.processID + "   "
                    + block.internalFragmentation);
        }

        scanner.close();
    }

    // First-Fit Allocation
    public static boolean allocateFirstFit(MemoryBlock[] memory, String processID, int processSize) {
        for (MemoryBlock block : memory) {
            if (block.status.equals("free") && block.blockSize >= processSize) {
                block.status = "allocated";
                block.processID = processID;
                block.internalFragmentation = block.blockSize - processSize;
                System.out.println(processID + " allocated at address: " + block.startAddress +
                        ", internal fragmentation: " + block.internalFragmentation);
                return true;
            }
        }
        System.out.println("Error: No suitable block found for process " + processID);
        return false;
    }

    // Best-Fit Allocation
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
            System.out.println(processID + " allocated at address: " + bestBlock.startAddress +
                    ", internal fragmentation: " + bestBlock.internalFragmentation);
            return true;
        }

        System.out.println("Error: No suitable block found for process " + processID);
        return false;
    }

    // Worst-Fit Allocation
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
            System.out.println(processID + " allocated at address: " + worstBlock.startAddress +
                    ", internal fragmentation: " + worstBlock.internalFragmentation);
            return true;
        }

        System.out.println("Error: No suitable block found for process " + processID);
        return false;
    }
}