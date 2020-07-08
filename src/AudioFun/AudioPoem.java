package AudioFun;

import BookClasses.FileChooser;
import BookClasses.Sound;
import BookClasses.SoundException;
import BookClasses.SoundSample;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 * This class contains methods for mixing up the words in an audio file and
 * creating sound poetry out of them. It contains many stub methods which need
 * to be completed as part of Assignment 1.
 *
 * The method waveAudio is my own poetry method where I make the volume go in waves.
 * @author clatulip, Julia Terry
 */
public class AudioPoem {

    static final int MAX_NUM_WORDS = 100;
    static private Sound[] myWordArray = new Sound[MAX_NUM_WORDS];
    static private int numWords = 0;

    public AudioPoem(Sound originalSource, int[] spliceArray, int numSplicePoints) {
        // break the sound into sepearate words, copying each into the word array
        for (int i = 0, j = 0; i < numSplicePoints; i = i + 2, j++) {
            myWordArray[j] = new Sound(spliceArray[i + 1] - spliceArray[i]);
            for (int x = spliceArray[i], y = 0; x < spliceArray[i + 1]; x++, y++) {
                myWordArray[j].setSampleValueAt(y, originalSource.getSampleValueAt(x));
            }
            numWords++;
        }
    }

    /**
     * Plays the words, in order with a 200 millisecond pause between each
     *
     * @throws InterruptedException
     */
    public void play() throws InterruptedException {
        // play the words in order using a for loop
        for (int i = 0; i < numWords; i++) {
            myWordArray[i].blockingPlay();
            Thread.sleep(200);
        }
    }

    /**
     * Plays the words, in order with a parameter-specified pause between each
     *
     * @param pause the number of milliseconds to pause between words
     * @throws InterruptedException
     */
    public void play(int pause) throws InterruptedException {
        // play the words in order using a for loop, but adding the play parameter
        for (int i = 0; i < numWords; i++) {
            myWordArray[i].blockingPlay();
            Thread.sleep(pause);
        }
    }

    /**
     * Plays the words in random order, each word can be played multiple times
     *
     * @param totalWords the total number of words that will be played
     * @param pause the number of milliseconds to pause between words
     * @throws InterruptedException
     */
    public void playRandomOrder(int totalWords, int pause) throws InterruptedException {
        // generates a random number and then plays what is in that part of myWordArray
        // using the same parameter for pause
        int random = 0;
        for (int i = 0; i < totalWords; i++) {
            random = (int) (Math.random()*numWords);
            myWordArray[random].blockingPlay();
            Thread.sleep(pause);
        }
    }

    /**
     * Plays the words in random order, playing each word only once
     *
     * @param pause the number of milliseconds to pause between words
     * @throws InterruptedException
     */
    public void playRandomUnique(int pause) throws InterruptedException {
        // fill an arraylist with random integers
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (arrayList.size()<numWords) {
            int random = (int) (Math.random()*numWords);
            if(!arrayList.contains(random)){
                arrayList.add(random);
            }
        }
        // go through arrayList and play myWordArray at that value
        for(Integer i : arrayList){
            myWordArray[i].blockingPlay();
            Thread.sleep(pause);
        }
    }

    /**
     * Plays the sound words in reverse order (e.g. 'this is a test' will be
     * played 'test a is this')
     *
     * @param pause the number of milliseconds to pause between words
     * @throws InterruptedException
     */
    public void playReverseOrder(int pause) throws InterruptedException {
        // make a new arrayList and fill it with the numbers going backwards
        int arrayList[] = new int[numWords];
        int i = numWords-1;
        for (int j = 0; j < numWords; j++){
                arrayList[j] = i;
                i--;
        } 
        // use the array to iterate through myWordArray backwards
        for (int k : arrayList){
            myWordArray[k].blockingPlay(); 
            Thread.sleep(pause);
        }
    }

    
    /**
     * Plays the words, in order with a parameter-specified pause between each 
     * and writes the resulting sound out to a file
     * @param pause the number of milliseconds to pause between words
     * @param filename the name of the file to write
     * @param path the path where the file should be written
     * @throws InterruptedException
     */
    public void play(int pause, String path, String filename) throws InterruptedException, SoundException {
        // find the sampleNum by getting the seconds sample number and iterating through
        // myWordArray and getting its number of samples
        int sampleNum = (int) (pause / 1000.0 * (myWordArray[0].getSamplingRate()));;
        int sampleValue = 0;;
        Sound newSound = new Sound(sampleNum);
        for (int j = 0; j < numWords; j++) {
            sampleNum += myWordArray[j].getNumSamples();
        }
        // iterate through myWordArray to copy it to newSound
        for (int i = 0; i < sampleNum; i++) {
            myWordArray[i].blockingPlay();
            Thread.sleep(pause);
            sampleValue = myWordArray[i].getSampleValueAt(i);
            newSound.setSampleValueAt(i, sampleValue);
        }    
        // write the new sound to the path+filename
        newSound.writeToFile(path+filename);
    }
 


    /**
     * Plays the words in random order, each word can be played multiple times 
     * and writes the resulting sound out to a file
     *
     * @param totalWords the total number of words that will be played
     * @param pause the number of milliseconds to pause between words
     * @param filename the name of the file to write
     * @param path the path where the file should be written
     * @throws InterruptedException
     */
    public void playRandomOrder(int totalWords, int pause, String filename, String path) throws InterruptedException {
        // generates a random number and then plays what is in that part of myWordArray
        // using the same parameter for pause
        for (int i = 0; i < totalWords; i++) {
            int random = (int) (Math.random()*numWords);
            myWordArray[random].blockingPlay();
            Thread.sleep(pause);
        }
    }

    /**
     * Plays the words in random order, playing each word only once and writes 
     * the resulting sound out to a file
     *
     * @param pause the number of milliseconds to pause between words
     * @param filename the name of the file to write
     * @param path the path where the file should be written
     * @throws InterruptedException
     */
    public void playRandomUnique(int pause, String filename, String path) throws InterruptedException {
        // fill an arraylist with random integers
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (arrayList.size()<numWords) {
            int random = (int) (Math.random()*numWords);
            if(!arrayList.contains(random)){
                arrayList.add(random);
            }
        }
        // go through arrayList and play myWordArray at that value
        for(Integer i : arrayList){
        myWordArray[i].blockingPlay();
        Thread.sleep(pause);
        }
    }

    /**
     * Plays the sound words in reverse order (e.g. 'this is a test' will be
     * played 'test a is this') and writes the resulting sound out to a file
     *
     * @param pause the number of milliseconds to pause between words
     * @param filename the name of the file to write
     * @param path the path where the file should be written
     * @throws InterruptedException
     */
    public void playReverseOrder(int pause, String filename, String path) throws InterruptedException {
        // make a new arrayList and fill it with the numbers going backwards
        int arrayList[] = new int[numWords];
        int i = numWords-1;
        for (int j = 0; j < numWords; j++){
            arrayList[j] = i;
            i--;
        }
        // use the array to iterate through myWordArray backwards
        for (int k : arrayList){
            myWordArray[k].blockingPlay(); 
            Thread.sleep(pause);
        }
    }

}
        
    

    
