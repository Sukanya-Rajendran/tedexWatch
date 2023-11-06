//const sendJsonButton = document.getElementById('b1');
//
//sendJsonButton.addEventListener('click', function() {
//  fetch('/offers/offer/sentResponse', {
//    method: 'GET',
//    headers: {
//      'Content-Type': 'application/json'
//    }
//  })
//  .then(response => {
//    if (response.ok) {
//    console.log(response.data)
////      return response.json() // Parse the response as text
//    } else {
//      throw new Error('Network response was not ok');
//    }
//  })
//  .then(data => {
//    console.log('Response data:', data); // Log the response data
//  })
//  .catch(error => {
//    console.error('There was a problem with the fetch operation:', error);
//  });
//});
//
//


const typeOfOfferSelect = document.getElementById('typeOfOffer');

typeOfOfferSelect.addEventListener('change', function() {
    const selectedValue = typeOfOfferSelect.value;

    // Prepare the data to send as JSON
    const requestData = { typeOfOffer: selectedValue };

    fetch('/offers/offer/selectOfferMethod', {
        method: 'POST', // Use POST instead of GET
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData) // Convert the data to a JSON string
    })
    .then(response => {
        if (response.ok) {
            return response.json(); // Parse the response as JSON, if needed
        } else {
            throw new Error('Network response was not ok');
        }
    })
    .then(data => {
        // Handle the response data here, if needed
        console.log('Response data:', data);
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
});
