document.addEventListener('DOMContentLoaded', function () {
    // Get all the rows with the class 'user-row'
    var rows = document.querySelectorAll('.user-row');

    // Get the delete button
    var deleteButton = document.getElementById('deleteButton'); // Change to the appropriate ID

    // Get the input field
    var selectedIdInput = document.getElementById('selectedId'); // Change to the appropriate ID

    // Add click event listeners to each row
    rows.forEach(function (row) {
        row.addEventListener('click', function () {
            // Toggle the 'selected' class for the clicked row
            if (row.classList.contains('selected')) {
                row.classList.remove('selected');
                // Clear the input field when a row is unselected
                selectedIdInput.textContent = '0';
                // Remove console logs when a row is unselected
                console.clear();
                // Disable the delete button when no row is selected
                deleteButton.disabled = true;
            } else {
                // Remove the 'selected' class from all rows
                rows.forEach(function (r) {
                    r.classList.remove('selected');
                });

                row.classList.add('selected');

                // Retrieve data from the clicked row directly using JavaScript
                var id = row.cells[0].innerText;
                // Update the input field with the selected ID
                selectedIdInput.textContent = id;

                // Log the data to the console for verification when a row is selected
                var userName = row.cells[1].innerText;
                var password = row.cells[2].innerText;
                var role = row.cells[3].innerText;
                console.log('ID:', id);
                console.log('Username:', userName);
                console.log('Password:', password);
                console.log('Role:', role);

                // Enable the delete button when a row is selected
                deleteButton.disabled = false;
            }
        });
    });

    // Add a click event listener to the delete button
    deleteButton.addEventListener('click', function () {
        // Fetch the data for the selected user (you may use AJAX to get the data from the server)
        var selectedId = selectedIdInput.textContent;
        var selectedUserName = document.querySelector('.user-row.selected td:nth-child(2)').textContent;
        var selectedPassword = document.querySelector('.user-row.selected td:nth-child(3)').textContent;
        var selectedRole = document.querySelector('.user-row.selected td:nth-child(4)').textContent;

        // Log the data to the console for verification
        console.log('Selected ID:', selectedId);
        console.log('Selected Username:', selectedUserName);
        console.log('Selected Password:', selectedPassword);
        console.log('Selected Role:', selectedRole);

        // Update the modal with the selected user's data
        document.getElementById('selectedUserIdDelete').textContent = selectedId;
        document.getElementById('deleteUsername').textContent = selectedUserName;
        document.getElementById('deletePassword').textContent = selectedPassword;
        document.getElementById('deleteRole').textContent = selectedRole;

        // Show the modal without jQuery
        var deleteConfirmationModal = document.getElementById('deleteConfirmationModal');
        deleteConfirmationModal.classList.add('show');
        deleteConfirmationModal.style.display = 'block';
        document.body.classList.add('modal-open');
    });

    // Add a click event listener to the close button in the modal header
    var closeButton = document.querySelector('#deleteConfirmationModal .modal-header .close');
    closeButton.addEventListener('click', function () {
        // Hide the modal without jQuery
        var deleteConfirmationModal = document.getElementById('deleteConfirmationModal');
        deleteConfirmationModal.classList.remove('show');
        deleteConfirmationModal.style.display = 'none';
        document.body.classList.remove('modal-open');
    });

    // Add a click event listener to the close button in the modal footer
    var closeFooterButton = document.querySelector('#deleteConfirmationModal .modal-footer .btn-secondary');
    closeFooterButton.addEventListener('click', function () {
        // Hide the modal without jQuery
        var deleteConfirmationModal = document.getElementById('deleteConfirmationModal');
        deleteConfirmationModal.classList.remove('show');
        deleteConfirmationModal.style.display = 'none';
        document.body.classList.remove('modal-open');
    });

    // Add a click event listener to the confirm delete button in the modal
    document.getElementById('confirmDeleteButton').addEventListener('click', function () {
        // Get the selected ID for deletion
        var selectedIdDelete = document.getElementById('selectedUserIdDelete').textContent;

        // Perform AJAX request to delete user data
        var xhr = new XMLHttpRequest();
        xhr.open('DELETE', '/TP06/User_Delete/' + selectedIdDelete, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // Reload the page after successful delete
                    reloadPage();
                } else {
                    console.error('Error deleting user:', xhr.status, xhr.statusText);
                }
            }
        };

        xhr.send();
    });

    // Function to reload the page
    function reloadPage() {
        location.reload();
    }
});
