// Get all input fields that should auto-resize
const autoResizeInputs = document.querySelectorAll('.auto-resize-input');

// Function to adjust the input width based on content
function adjustInputWidth(inputElement) {
    // Create a temporary span to measure the text width
    const tempSpan = document.createElement('span');
    tempSpan.style.visibility = 'hidden';
    tempSpan.style.position = 'absolute';
    tempSpan.style.whiteSpace = 'nowrap'; // Prevent text wrapping
    tempSpan.style.font = getComputedStyle(inputElement).font; // Match font styles
    tempSpan.style.letterSpacing = getComputedStyle(inputElement).letterSpacing; // Match letter spacing
    tempSpan.style.padding = getComputedStyle(inputElement).padding; // Match padding
    tempSpan.style.border = getComputedStyle(inputElement).border; // Match border
    tempSpan.style.boxSizing = getComputedStyle(inputElement).boxSizing; // Match box sizing

    // Use the input's current value, or placeholder if empty
    const textToMeasure = inputElement.value || inputElement.placeholder;
    tempSpan.textContent = textToMeasure;

    document.body.appendChild(tempSpan); // Add to DOM to measure
    const textWidth = tempSpan.offsetWidth; // Get the calculated width
    document.body.removeChild(tempSpan); // Remove the temporary span

    // Add a small buffer (e.g., 10-15 pixels) to prevent text from touching the edge
    inputElement.style.width = (textWidth + 15) + 'px';
}

// Attach event listeners to each auto-resize input
autoResizeInputs.forEach(input => {
    // Adjust width on input (as user types)
    input.addEventListener('input', () => adjustInputWidth(input));

    // Adjust width initially, in case there's a default value or placeholder
    adjustInputWidth(input);
});