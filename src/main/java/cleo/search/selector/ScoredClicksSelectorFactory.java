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

package cleo.search.selector;

import cleo.search.Element;

/**
 * ScoredClicksSelectorFactory
 * 
 * @author jwu
 * @since 02/18, 2011
 * 
 * <p>
 * 05/19, 2011 - Added default serialVersionUID <br/>
 */
public class ScoredClicksSelectorFactory<E extends Element> implements SelectorFactory<E> {
  private static final long serialVersionUID = 1L;

  @Override
  public Selector<E> createSelector(String... terms) {
    return new ScoredClicksSelector<E>(terms);
  }
}
