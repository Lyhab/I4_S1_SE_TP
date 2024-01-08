document.addEventListener('DOMContentLoaded', function () {
    // Get all the rows with the class 'product-row'
    var rows = document.querySelectorAll('.product-row');

    // Get the update button
    var updateButton = document.getElementById('updateButton');

    // Get the input field
    var selectedCodeInput = document.getElementById('selectedCode');

    // Add click event listeners to each row
    rows.forEach(function (row) {
        row.addEventListener('click', function () {
            // Toggle the 'selected' class for the clicked row
            if (row.classList.contains('selected')) {
                row.classList.remove('selected');
                // Clear the input field when a row is unselected
                selectedCodeInput.textContent = '0';
                // Remove console logs when a row is unselected
                console.clear();
                // Disable the update button when no row is selected
                updateButton.disabled = true;
                
            } else {
                // Remove the 'selected' class from all rows
                rows.forEach(function (r) {
                    r.classList.remove('selected');
                });

                row.classList.add('selected');

                // Retrieve data from the clicked row directly using JavaScript
                var id = row.cells[0].innerText;
                // Update the input field with the selected ID
                selectedCodeInput.textContent = id;

                // Log the data to the console for verification when a row is selected
                var productName = row.cells[1].innerText;
                var country = row.cells[2].innerText;
                var price = row.cells[3].innerText;
                var description = row.cells[4].innerText;

                console.log('Code:', id);
                console.log('Name:', productName);
                console.log('Country:', country);
                console.log('Price:', price);
                console.log('Description:', description);

                // Enable the update button when a row is selected
                updateButton.disabled = false;
            }
        });
    });

    // Add a click event listener to the update button
    updateButton.addEventListener('click', function () {
        // Fetch the data for the selected product
        var selectedCode = selectedCodeInput.textContent;
        var selectedProductName = document.querySelector('.product-row.selected td:nth-child(2)').textContent;
        var selectedCountry = document.querySelector('.product-row.selected td:nth-child(3)').textContent;
        var selectedPrice = document.querySelector('.product-row.selected td:nth-child(4)').textContent;
        var selectedDescription = document.querySelector('.product-row.selected td:nth-child(5)').textContent;

        // Log the data to the console for verification
        console.log('Selected Code:', selectedCode);
        console.log('Selected Name:', selectedProductName);
        console.log('Selected Country:', selectedCountry);
        console.log('Selected Price:', selectedPrice);
        console.log('Selected Description:', selectedDescription);

        // Update the modal with the selected products' data
        document.getElementById('selectedCode').value = selectedCode;
        document.getElementById('newProductName').value = selectedProductName;
        document.getElementById('newCountry').value = selectedCountry;        
        document.getElementById('newPrice').value = selectedPrice;       
        document.getElementById('newDescription').value = selectedDescription;  

        // Show the modal without jQuery
        var updateModal = document.getElementById('updateModal');
        updateModal.classList.add('show');
        updateModal.style.display = 'block';
        document.body.classList.add('modal-open');
    });

    // Add a click event listener to the close button in the modal header
    var closeButton = document.querySelector('#updateModal .modal-header .close');
    closeButton.addEventListener('click', function () {
        // Hide the modal without jQuery
        var updateModal = document.getElementById('updateModal');
        updateModal.classList.remove('show');
        updateModal.style.display = 'none';
        document.body.classList.remove('modal-open');
    });

    // Add a click event listener to the close button in the modal footer
    var closeFooterButton = document.querySelector('#updateModal .modal-footer .btn-secondary');
    closeFooterButton.addEventListener('click', function () {
        // Hide the modal without jQuery
        var updateModal = document.getElementById('updateModal');
        updateModal.classList.remove('show');
        updateModal.style.display = 'none';
        document.body.classList.remove('modal-open');
    });

    // Add a click event listener to the save changes button in the modal
    document.getElementById('saveChangesButton').addEventListener('click', function () {
        // Get the updated values from the modal
        var selectedCode = document.getElementById('selectedCode').value;
        var newProductName = document.getElementById('newProductName').value;
        var newCountry = document.getElementById('newCountry').value;        
        var newPrice = document.getElementById('newPrice').value;       
        var newDescription = document.getElementById('newDescription').value;  
    
        // Perform AJAX request to update user data
        var xhr = new XMLHttpRequest();
        xhr.open('PUT', '/TP06/Product_Update/' + selectedCode, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
    
        var updatedProductData = {
            productName: newProductName,
            country: newCountry,
            price: newPrice,
            description: newDescription
        };
    
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // Reload the page after a successful update
                    reloadPage();
                } else {
                    console.error('Error updating product:', xhr.status, xhr.statusText);
                }
            }
        };
    
        xhr.send(JSON.stringify(updatedProductData));
    });
    
    // Function to reload the page
    function reloadPage() {
        location.reload();
    }
});
