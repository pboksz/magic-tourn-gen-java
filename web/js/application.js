function dropCheck(who)
{
   return confirm("Do you really want to drop [ " + who + " ] from the tournament?");
}

function addRounds(numPlayers)
{
   if((numPlayers >= 4) && numPlayers <= 8){
      clearRounds()
   }
   if((numPlayers > 8) && (numPlayers <= 16)) {
      clearRounds();
      addOptions(1);
   }
   if((numPlayers > 16) && (numPlayers <= 32)){
      clearRounds();
      addOptions(2);
   }
}

function clearRounds() {
   document.settings.howManyRounds.options.length = 1;
}

function addOptions(num) {
   for(var i=1; i<(num+1); i++){
      document.settings.howManyRounds.options[i] = new Option(i+3);
   }
}

//TODO still testing this, but coloring works individually
function verifyValue(field, maxNum) {
   //if there is an error do a red outline
   if(field.value > maxNum){
      field.style.borderColor = "#ff8888";
      field.style.mozBoxShadow = "0 0 0.25em #ff8888";
      field.style.boxShadow = "0 0 0.25em #ff8888";
   }
   //else do a blue one
   else {
      field.style.borderColor = "#b2d1ff";
      field.style.mozBoxShadow = "0 0 0.25em #b2d1ff";
      field.style.boxShadow = "0 0 0.25em #b2d1ff";
   }

   //TODO write a verify all values method
   function verifyValues(bestOf, maxWin) {
      var hasNoError = true;
      var wins = document.show.wins;
      var losses = document.show.losses;

      for(var i=0; i<wins.length; i++){
         var color;
         if((wins[i]+losses[i] > bestOf) || (wins[i]+losses[i] < maxWin)){
            hasNoError = false;
            color = "#ff8888";
         }
         else {
            color = "#b2d1ff"
         }
         wins[i].style.borderColor = color;
         wins[i].style.mozBoxShadow = "0 0 0.25em " + color;
         wins[i].style.boxShadow = "0 0 0.25em " + color;
         losses[i].style.borderColor = color;
         losses[i].style.mozBoxShadow = "0 0 0.25em " + color;
         losses[i].style.boxShadow = "0 0 0.25em " + color;
      }
      return hasNoError;
   }
}