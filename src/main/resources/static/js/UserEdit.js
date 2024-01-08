document.addEventListener('DOMContentLoaded', function () {
    // Get the edit button
    var editButton = document.getElementById('editButton');

    // Get the input fields in the modal
    var selectedUserIdInput = document.getElementById('selectedUserId');
    var newUsernameInput = document.getElementById('newUsername');
    var newPasswordInput = document.getElementById('newPassword');

    // Get the modal and its close buttons
    var editModal = document.getElementById('editModal');
    var closeButtons = document.querySelectorAll('#editModal .close, #editModal .btn-secondary');

    // Add a click event listener to the edit button
    editButton.addEventListener('click', function () {
        // Retrieve data from the displayed information
        var selectedUserId = document.querySelector('.card-body p:nth-child(1) span').innerText;
        var newUsername = document.querySelector('.card-body p:nth-child(2) span').innerText;
        var newPassword = document.querySelector('.modal-body #newPassword').value;

        // Log the data to the console for verification
        console.log('Selected User ID:', selectedUserId);
        console.log('Selected Username:', newUsername);
        console.log('Selected Password:', newPassword);

        // Update the modal with the selected user's data
        selectedUserIdInput.value = selectedUserId;
        newUsernameInput.value = newUsername;
        newPasswordInput.type = 'text'; // Change the type to display actual characters
        newPasswordInput.value = newPassword; // Use the retrieved password value

        // Show the modal without jQuery
        editModal.classList.add('show');
        editModal.style.display = 'block';
        document.body.classList.add('modal-open');
    });

    // Add a click event listener to the save changes button in the modal
    document.getElementById('saveChangesButton').addEventListener('click', function () {
        // Get the updated values from the modal
        var selectedUserId = selectedUserIdInput.value;
        var newUsername = newUsernameInput.value;
        var newPassword = newPasswordInput.value;

        // Perform AJAX request to update only username
        var xhr = new XMLHttpRequest();
        xhr.open('PUT', '/TP06/User_Edit/' + selectedUserId, true);
        xhr.setRequestHeader('Content-Type', 'application/json');

        var updatedUserData = {
            userName: newUsername,
            password: newPassword
        };

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // Update the Thymeleaf expressions with the new values
                    document.getElementById('username').innerText = newUsername;
                    
                    // Update the welcome message
                    document.getElementById('welcomeUsername').innerHTML = 'Welcome <span>' + newUsername + '</span>';

                    // Hide the modal without jQuery
                    editModal.classList.remove('show');
                    editModal.style.display = 'none';
                    document.body.classList.remove('modal-open');
                } else {
                    console.error('Error updating user:', xhr.status, xhr.statusText);
                }
            }
        };

        xhr.send(JSON.stringify(updatedUserData));
    });

    // Add click event listeners to close buttons
    closeButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            // Hide the modal without jQuery
            editModal.classList.remove('show');
            editModal.style.display = 'none';
            document.body.classList.remove('modal-open');
        });
    });

    // Add a click event listener to the modal backdrop
    editModal.addEventListener('click', function (event) {
        if (event.target === editModal) {
            // Hide the modal without jQuery when clicking outside the modal
            editModal.classList.remove('show');
            editModal.style.display = 'none';
            document.body.classList.remove('modal-open');
        }
    });

    // Function to reload the page
    function reloadPage() {
        location.reload();
    }
});
