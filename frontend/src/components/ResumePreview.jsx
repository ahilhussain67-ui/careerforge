function ResumePreview({ resume }) {
  return (
    <div className="resume-preview">
      <div className="resume-header">
        <h1>{resume.name || "Your Name"}</h1>

        <p>
          {resume.email || "email@example.com"}
          {" | "}
          {resume.phone || "Phone Number"}
        </p>
      </div>

      <section>
        <h2>Professional Summary</h2>
        <p>
          {resume.summary || "Your professional summary will appear here."}
        </p>
      </section>

      <section>
        <h2>Skills</h2>
        <p>
          {resume.skills || "Your skills will appear here."}
        </p>
      </section>

      <section>
        <h2>Education</h2>
        <p>
          {resume.education || "Your education details will appear here."}
        </p>
      </section>

      <section>
        <h2>Experience</h2>
        <p>
          {resume.experience || "Your experience details will appear here."}
        </p>
      </section>

      <section>
        <h2>Projects</h2>
        <p>
          {resume.projects || "Your project details will appear here."}
        </p>
      </section>
    </div>
  );
}

export default ResumePreview;