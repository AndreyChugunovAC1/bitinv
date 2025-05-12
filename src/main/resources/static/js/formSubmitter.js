setListener = (elemId, url, formDataProcessor, dataProcessor) => {
  document.getElementById(elemId).addEventListener('submit', async function (e) {
    e.preventDefault();

    const formData = new FormData(e.target);

    formDataProcessor(formData);
    try {
      const response = await fetch(url, {
        method: 'POST',
        body: formData
      });

      const data = await response.json();

      if (!response.ok) {
        alert(data.error);
      } else {
        dataProcessor(data);
      }
    } catch (err) {
      console.error("Error:", err);
      alert("An error occurred: " + err.message);
    }
  });
}