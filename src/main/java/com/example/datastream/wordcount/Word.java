package com.example.datastream.wordcount;

/**
 * @author luxiaohua
 * @create 2022-06-27 16:08
 */
public class Word {

    private String word;

    private Long frequency;

    /**
     *   一定要有空参构造方法
     */
    public Word() {
    }

    public Word(String word, Long frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
