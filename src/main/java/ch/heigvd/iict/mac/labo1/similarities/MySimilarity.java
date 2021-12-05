package ch.heigvd.iict.mac.labo1.similarities;

import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.util.MathUtil;

public class MySimilarity extends ClassicSimilarity {

    // TODO student
    // Implement the functions described in section "Tuning the Lucene Score"

    @Override
    public float tf(float freq){

        return (float) (1.0f+Math.log(freq));
    }

    @Override
    public float idf(long docFreq, long numDocs){
        return (float) (Math.log(numDocs/(docFreq+1)) + 1);
    }

    @Override
    public float lengthNorm(int numTerms){
        return 1;
    }
}
