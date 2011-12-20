/*
 * Copyright (c) 2011 LinkedIn, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package cleo.search.filter;

import cleo.search.Element;
import krati.util.Fnv1Hash32;

/**
 * FnvBloomFilter - A bloom filter based on 32-Bit FNV-1a Hash.
 * 
 * @author jwu
 * @since 01/14, 2011
 */
public class FnvBloomFilter implements BloomFilter<Integer> {
  public final static int NUM_BITS = 32;
  
  private final int prefixLength;
  
  public FnvBloomFilter(int prefixLength) {
    this.prefixLength = prefixLength;
  }
  
  @Override
  public final int getNumBits() {
    return NUM_BITS;
  }
  
  @Override
  public Integer computeIndexFilter(Element element) {
    int filter = 0;
    for(String s : element.getTerms()) {
      if(s != null) {
        filter |= computeBloomFilter(s, prefixLength);
      }
    }
    
    return filter;
  }
  
  @Override
  public Integer computeQueryFilter(String value) {
    return value == null ? 0 : computeBloomFilter(value, prefixLength);
  }
  
  @Override
  public Integer computeQueryFilter(String... values) {
    int filter = 0;
    for(String s : values) {
      if(s != null) {
        filter |= computeBloomFilter(s, prefixLength);
      }
    }
    
    return filter;
  }
  
  public static final int computeBloomFilter(String s, int prefixLength) {
    int cnt = Math.min(prefixLength, s.length());
    if (cnt <= 0) return 0;
    
    int filter = 0;
    int bitpos = 0;
    
    long hash = Fnv1Hash32.FNV_BASIS;
    for(int i = 0; i < cnt; i++) {
      char c = s.charAt(i);
      
      hash ^= 0xFF & c;
      hash *= Fnv1Hash32.FNV_PRIME;
      hash &= Fnv1Hash32.BITS_MASK;
      
      hash ^= 0xFF & (c >> 8);
      hash *= Fnv1Hash32.FNV_PRIME;
      hash &= Fnv1Hash32.BITS_MASK;
      
      bitpos = (int)(hash % NUM_BITS);
      if(bitpos < 0) bitpos += NUM_BITS;
      filter |= 1 << bitpos;
    }
      
    return filter;
  }
}
