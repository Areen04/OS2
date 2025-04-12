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
}
