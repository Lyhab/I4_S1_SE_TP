document.addEventListener('DOMContentLoaded', function () {
    // Get the search input and search button
    var searchInput = document.getElementById('searchInput');
    var searchButton = document.getElementById('searchButton'); // Updated to use the correct ID

    // Check if the search button and input are present
    if (searchInput && searchButton) {
        // Add a click event listener to the search button
        searchButton.addEventListener('click', function () {
            // Get the value from the search input
            var searchTerm = searchInput.value.trim().toLowerCase();

            // Get all the rows in the table body
            var rows = document.querySelectorAll('.table tbody tr');

            // Loop through each row and check if it contains the search term
            rows.forEach(function (row) {
                var orderNumber = row.cells[1].textContent.toLowerCase();

                // Show or hide the row based on the search term
                if (orderNumber.includes(searchTerm)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
    }
});
