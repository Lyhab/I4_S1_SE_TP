document.addEventListener('DOMContentLoaded', function () {
    // Get all the rows with the class 'user-row'
    var rows = document.querySelectorAll('.user-row');

    // Get the update button
    var updateButton = document.getElementById('updateButton');

    // Get the input field
    var selectedIdInput = document.getElementById('selectedId');

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
                selectedIdInput.textContent = id;

                // Log the data to the console for verification when a row is selected
                var userName = row.cells[1].innerText;
                var password = row.cells[2].innerText;
                var role = row.cells[3].innerText;
                console.log('ID:', id);
                console.log('Username:', userName);
                console.log('Password:', password);
                console.log('Role:', role);

                // Enable the update button when a row is selected
                updateButton.disabled = false;
            }
        });
    });

    // Add a click event listener to the update button
    updateButton.addEventListener('click', function () {
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
        document.getElementById('selectedUserId').value = selectedId;
        document.getElementById('newUsername').value = selectedUserName;
        document.getElementById('newPassword').type = 'text';
        document.getElementById('newPassword').value = selectedPassword;        

        // Update the selected role in the dropdown
        var newRole = document.getElementById('newRole');
        newRole.value = selectedRole;

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
        var selectedId = document.getElementById('selectedUserId').value;
        var newUsername = document.getElementById('newUsername').value;
        var newPassword = document.getElementById('newPassword').value;
        var newRole = document.getElementById('newRole').value;

        // Perform AJAX request to update user data
        var xhr = new XMLHttpRequest();
        xhr.open('PUT', '/TP06/User_Update/' + selectedId, true);
        xhr.setRequestHeader('Content-Type', 'application/json');

        var updatedUserData = {
            userName: newUsername,
            password: newPassword,
            role: newRole
        };

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // Reload the page after successful update
                    reloadPage();
                } else {
                    console.error('Error updating user:', xhr.status, xhr.statusText);
                }
            }
        };

        xhr.send(JSON.stringify(updatedUserData));
    });
    
    // Function to reload the page
    function reloadPage() {
        location.reload();
    }
});