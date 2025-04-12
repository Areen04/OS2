/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pro;

public class MemoryBlock {
    int blockSize;                 // Size of the block in KB
    int startAddress;              // Starting address of the block
    int endAddress;                // Ending address of the block
    String status;                 // "free" or "allocated"
    String processID;              // ID of the process in this block, or "null" if empty
    int internalFragmentation;     // Fragmentation inside the block (0 by default)

    // Constructor
    public MemoryBlock(int blockSize, int startAddress) {
        this.blockSize = blockSize;
        this.startAddress = startAddress;
        this.endAddress = startAddress + blockSize - 1;
        this.status = "free"; // All blocks start as free
        this.processID = "null"; // No process yet
        this.internalFragmentation = 0; // Default fragmentation is zero
    }
}