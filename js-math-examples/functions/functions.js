// Task 1
function getEvenNumbers(start, end) {
  let count = 0;
  for (let i = start; i <= end; i++) {
    if (i % 2 === 0) {
      count++;
    }
  }
  return count;
}

// Task 2
function getOccurencesOfChar(str, char) {
  str = str.toLowerCase();
  char = char.toLowerCase();
  
  let count = 0;
  for (let i = 0; i < str.length; i++) {
    if (str[i] === char) {
      count++;
    }
  }
  return count;
}


// Task 3
function moveVector(x, y, scalar) {
  return {
    x: x + scalar,
    y: y + scalar
  };
}

// Task 4
function arrayFunction(param) {
  return function() {
    if (param % 2 === 0) {
      return 'Is even';
    } else {
      return 'Not even';
    }
  };
}

// Task 5 & Task 6
function switchFunction(param) {
  if (typeof param === 'string') {
    return 'str';
  } else if (typeof param === 'number') {
    return 'numb';
  } else if (typeof param === 'boolean') {
    return 'bool';
  } else if (typeof param === 'function') {
    return param();
  } else {
    return undefined;
  }
}

// Task 7
function fibonacci(n) {
  if (n <= 1) return n;
  return fibonacci(n - 1) + fibonacci(n - 2);
}

// Task 8
function factorialize(num) {
  if (num === 0 || num === 1) return 1;
  return num * factorialize(num - 1);
}

// Task 9
function returnPowerOf(base, exponent) {
  return Math.pow(base, exponent);
}

// Task 10
function convertFtoC(fahrenheit) {
  return (fahrenheit - 32) * 5 / 9;
}

// Exporting the functions
module.exports = {
  getEvenNumbers,
  getOccurencesOfChar,
  moveVector,
  arrayFunction,
  switchFunction,
  fibonacci,
  factorialize,
  returnPowerOf,
  convertFtoC
};

