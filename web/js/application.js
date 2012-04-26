function dropCheck(who)
{
   return confirm("Do you really want to drop [ " + who + " ] from the tournament?");
}

function addRounds(numPlayers)
{
   if ((numPlayers >= 4) && numPlayers <= 8)
   {
      clearRounds()
   }
   if ((numPlayers > 8) && (numPlayers <= 16))
   {
      clearRounds();
      addOptions(1);
   }
   if ((numPlayers > 16) && (numPlayers <= 32))
   {
      clearRounds();
      addOptions(2);
   }
   if ((numPlayers > 32) && (numPlayers <= 64))
   {
      clearRounds();
      addOptions(3);
   }
   if ((numPlayers > 64) && (numPlayers <= 128))
   {
      clearRounds();
      addOptions(4);
   }
   if ((numPlayers > 128) && (numPlayers <= 256))
   {
      clearRounds();
      addOptions(5);
   }
   if ((numPlayers > 256) && (numPlayers <= 512))
   {
      clearRounds();
      addOptions(6);
   }
}

function clearRounds()
{
   document.settings.howManyRounds.options.length = 1;
}

function addOptions(num)
{
   for (var i = 1; i < (num + 1); i++)
   {
      document.settings.howManyRounds.options[i] = new Option(i + 3);
   }
}

//TODO still testing this, but coloring works individually
function verifyValue(field, maxNum)
{
   //if there is an error do a red outline
   if (field.value > maxNum)
   {
      field.style.borderColor = "#ff8888";
      field.style.mozBoxShadow = "0 0 0.25em #ff8888";
      field.style.boxShadow = "0 0 0.25em #ff8888";
   }
   //else do a blue one
   else
   {
      field.style.borderColor = "#b2d1ff";
      field.style.mozBoxShadow = "0 0 0.25em #b2d1ff";
      field.style.boxShadow = "0 0 0.25em #b2d1ff";
   }
}

//TODO testing this to check that the sum of the two are valid, should highlight the incorrect rows
//TODO should send an error?
function verifyValues(bestOf, maxWins) {
   var isValid = true;
   var wins = document.show.wins;
   var losses = document.show.losses;

   for(var i = 0; i < wins.length; i++){
      var win = parseInt(wins[i].value);
      var loss = parseInt(losses[i].value);
      if((win != -1) && (loss != -1)){
         var table = document.getElementById("showtable");
         var row = table.tBodies[0].rows[i];
         var sum = win + loss;
         if((sum > bestOf) || (sum < maxWins)) {
//            row.style.borderBottomWidth = "2px";
//            row.style.borderLeftWidth = "2px";
//            row.style.borderRightWidth = "2px";
//            row.style.borderColor = "#ff8888";
//            isValid = false;
         }
         else {
//            row.style.borderBottomWidth = "2px";
//            row.style.borderLeftWidth = "2px";
//            row.style.borderRightWidth = "2px";
//            row.style.borderColor = "#b2d1ff";
         }
      }
   }
   return isValid;
}