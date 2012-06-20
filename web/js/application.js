function dropCheck(who) {
   return confirm("Do you really want to drop [ " + who + " ] from the tournament?");
}

function addRounds(numPlayers) {
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

function clearRounds() {
   document.settings.howManyRounds.options.length = 1;
}

function addOptions(num) {
   for (var i = 1; i < (num + 1); i++)
   {
      document.settings.howManyRounds.options[i] = new Option(i + 3);
   }
}


function verifyValue(inputElement, maxWins, bestOf) {
   var row = inputElement.parentElement.parentElement;
   var winElement = row.cells[1].firstElementChild;
   var lossElement = row.cells[4].firstElementChild;
   var winString = winElement.value;
   var lossString = lossElement.value;

   //if both wins and losses are filled in, else just color the input changed
   if(winString != "" && lossString != "") {
      //get the integer values of win and loss
      var win = parseInt(winString);
      var loss = parseInt(lossString);

      //if win and loss sum to greater than bestOf
      //CASE: win is 2 and loss is 2 in a bestOf 3 tournament
      if(win + loss > bestOf) {
         error(winElement);
         error(lossElement);
      }
      //if one of the values is not the maxWins
      //CASE: win and loss are both 1 which doesn't show a clear winner in a bestOf 3 tournament
      else if(!((win == maxWins) || (loss == maxWins))) {
         error(winElement);
         error(lossElement);
      }
      //else these should be valid values
      else {
         valid(winElement);
         valid(lossElement);
      }
   } else {
      //if there is an error do a red outline, else a blue outline
      if (inputElement.value > maxWins) {
         error(inputElement);
      } else {
         valid(inputElement)
      }
   }
}

function error(inputElement) {
   inputElement.style.borderColor = "#ff8888";
   inputElement.style.mozBoxShadow = "0 0 0.25em #ff8888";
   inputElement.style.webkitBoxShadow = "0 0 0.25em #ff8888";
   inputElement.style.boxShadow = "0 0 0.25em #ff8888";
}

function valid(inputElement) {
   inputElement.style.borderColor = "#b2d1ff";
   inputElement.style.mozBoxShadow = "0 0 0.25em #b2d1ff";
   inputElement.style.webkitBoxShadow = "0 0 0.25em #b2d1ff";
   inputElement.style.boxShadow = "0 0 0.25em #b2d1ff";
}

function verifyValues(maxWins, bestOf) {
   var wins = document.show.wins;
   var losses = document.show.losses;

   for(var i = 0; i < wins.length; i++) {
      //if the borderColor is red then there is an error and cannot move forward
      //TODO test this on other browsers. Chrome, Firefox, Opera are good. Need to check IE and Safari.
      if((wins[i].style.borderColor == "rgb(255, 136, 136)") || losses[i].style.borderColor == "rgb(255, 136, 136)") {
         document.getElementById("mainError").style.display = "block";
         return false;
      }
   }
   return true;
}
