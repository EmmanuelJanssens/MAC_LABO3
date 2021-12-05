package ch.heigvd.iict.mac.labo1.similarities;

import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.util.MathUtil;

public class MySimilarity extends ClassicSimilarity {

    // 5. Tunning the lucene score

    @Override
    public float tf(float freq){
        return(float) (Math.log(freq)) + 1.0f;
    }
    @Override
    public float idf(long docFreq, long numDocs){
        return (float) (Math.log(numDocs/(docFreq+1.0f)))  + 1.0f;
    }
    @Override
    public float lengthNorm(int numTerms){
        return 1;
    }
}
