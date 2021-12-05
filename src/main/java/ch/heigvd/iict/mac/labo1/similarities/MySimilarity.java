package ch.heigvd.iict.mac.labo1.similarities;

import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.util.MathUtil;

public class MySimilarity extends ClassicSimilarity {

    // TODO student
    // Implement the functions described in section "Tuning the Lucene Score"


    @Override
    public float lengthNorm(int numTerms){
        return 1.0f;
    }
}
