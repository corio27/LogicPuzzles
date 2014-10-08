/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package puzzle.swing;

import javax.swing.event.EventListenerList;

public abstract class AbstractPuzzleGridModel {
   private EventListenerList listenerList = new EventListenerList();

   public void addPuzzleGridListener(PuzzleGridListener l){
      listenerList.add(PuzzleGridListener.class, l);
   }
   
   public void removePuzzleGridListener(PuzzleGridListener l){
      listenerList.remove(PuzzleGridListener.class, l);
   }

   private void forwardEvent(PuzzleGridEvent ev){
       // Guaranteed to return a non-null array
       Object[] listeners = listenerList.getListenerList();
       // Process the listeners last to first, notifying
       // those that are interested in this event
       for (int i = listeners.length - 2; i >= 0; i -= 2){
           if (listeners[i] == PuzzleGridListener.class){
               ((PuzzleGridListener) listeners[i + 1]).restart(ev);
           }
       }
   }
   
   protected void fireRestartEvent(PuzzleGridEvent ev) {
       forwardEvent(ev);
   }
   
   protected void fireCellUpdatedEvent(PuzzleGridEvent ev) {
       forwardEvent(ev);
   }
}
