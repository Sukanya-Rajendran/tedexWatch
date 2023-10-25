      console.log("hiiii")
          function saveOrder() {

    var paymentStatus = document.getElementById("paymentMethod").textContent;
    var addressId = document.getElementById("addressId").textContent;
    console.log(addressId+"addressId=");
    const data = {
        paymentStatus: paymentStatus,
        addressId: addressId

    };

    // Send an AJAX POST request to the backend endpoint
    $.ajax({
        type: "POST",
        url: "/saveOrder",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (response) {


                        window.location.href = "/orderSuccess";

                        console.log("Order was not saved successfully.");


        },
        error: function (xhr, status, error) {
            console.log("Error occurred while saving the order:", error);

        }
    });
}





const buy = document.querySelector("#buy");
const offerPrice = document.getElementById("total").textContent;

            buy.addEventListener("click", () => {

console.log('offer pridce',offerPrice)
console.log('typr', typeof(offerPrice))
  const paymentStatus = document.querySelector('input[name="paymentMethod"]:checked');
  console.log(paymentStatus.value,paymentStatus , " hello")
 if (paymentStatus.value === "ONLINE") {
 console.log(offerPrice)
    $.ajax({
      type: "get",
      url: '/payment/checkout',
      data:   {total: offerPrice} , // sending the offer price to the backend as data
      success: function (response) {
        console.log(response);
        const orderId = response
        console.log(response)
        // Update relevant elements in the Thymeleaf template
        $('#successMessage').text(response);
        fetch(`/payment/confirm?orderId=${orderId}`, {
                        method: 'get',
                        headers: {
                            'Content-Type': 'application/json'
                        },
//                        body: JSON.stringify({ orderId: orderId })
                    })
                    .then(function (response) {
                        return response.json();
                    })
                    .then(function (response) {
                        var options = {
                            key: response.key,
                            currency: 'INR',
                            amount: response.amount,
                            order_id: response.id,
                            name: 'Pedal Planet',
                            description: 'Online Payment',
                            handler: function (response) {

                            console.log(response)
                                saveOrder();

                            },


                            prefill: {
                                name:  response.customerName,
                                email: response.customerEmail,
                                contact: response.customerContact,
                            }
                        };

                        var rzp1 = new Razorpay(options);
                        rzp1.open();
                    });
      },
      error: function (xhr, status, error) {
        console.log('Caught the error from the backend',error);
        // Handle error case if needed
      },
      contentType: "text/plain"
    });
  } else if (paymentStatus.value === "COD") {
    saveOrder();
    console.log("payment cash on delivery order")
  }else if (paymentStatus.value === "WALLET"){
  console.log("payment wallet order")
   walletPayment();
  }
});
