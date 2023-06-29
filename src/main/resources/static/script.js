document.getElementById("uploadForm").addEventListener("submit", function(event) {
    event.preventDefault();

    var form = event.target;
    var formData = new FormData(form);

    fetch("/images/upload", {
        method: "POST",
        body: formData
    })
    .then(function(response) {
        if (response.ok) {
            alert("Image uploaded successfully!");
            form.reset();
            fetchImages();
        } else {
            response.text().then(function(errorMessage) {
                alert("Failed to upload image: " + errorMessage);
            });
        }
    })
    .catch(function(error) {
        alert("Failed to upload image: " + error.message);
    });
});

function fetchImages() {
    fetch("/images")
    .then(function(response) {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Failed to fetch images");
        }
    })
    .then(function(images) {
        var imageGallery = document.getElementById("imageGallery");
        imageGallery.innerHTML = "";

        images.forEach(function(image) {
            var imgElement = document.createElement("img");
            imgElement.src = image.filePath;
            imageGallery.appendChild(imgElement);
        });
    })
    .catch(function(error) {
        alert(error.message);
    });
}

// Fetch images on page load
fetchImages();