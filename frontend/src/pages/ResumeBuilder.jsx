import { useState } from "react";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";
import AppShell from "../components/AppShell";
import ResumePreview from "../components/ResumePreview";

const emptyResume = (user) => ({ name: user.name, email: user.email, phone: "", summary: "", skills: "", education: "", experience: "", projects: "" });

export default function ResumeBuilder({ user, page, onNavigate, onLogout }) {
  const storageKey = `careerforge-resume-${user.id}`;
  const [resume, setResume] = useState(() => {
    try { return { ...emptyResume(user), ...JSON.parse(localStorage.getItem(storageKey) || "{}") }; }
    catch { return emptyResume(user); }
  });
  const [downloading, setDownloading] = useState(false);
  const change = (event) => {
    const next = { ...resume, [event.target.name]: event.target.value };
    setResume(next);
    localStorage.setItem(storageKey, JSON.stringify(next));
  };
  const download = async () => {
    setDownloading(true);
    try {
      const preview = document.querySelector(".resume-preview");
      if (!preview) throw new Error("Resume preview is unavailable.");
      const canvas = await html2canvas(preview, { scale: 2, backgroundColor: "#ffffff" });
      const pdf = new jsPDF("p", "mm", "a4");
      const pageWidth = pdf.internal.pageSize.getWidth();
      const pageHeight = pdf.internal.pageSize.getHeight();
      const imageHeight = canvas.height * pageWidth / canvas.width;
      const pageCanvas = document.createElement("canvas");
      const pagePixels = Math.floor(canvas.width * pageHeight / pageWidth);
      for (let offset = 0; offset < canvas.height; offset += pagePixels) {
        pageCanvas.width = canvas.width;
        pageCanvas.height = Math.min(pagePixels, canvas.height - offset);
        pageCanvas.getContext("2d").drawImage(canvas, 0, offset, canvas.width, pageCanvas.height, 0, 0, canvas.width, pageCanvas.height);
        if (offset > 0) pdf.addPage();
        const sliceHeight = pageCanvas.height * pageWidth / pageCanvas.width;
        pdf.addImage(pageCanvas.toDataURL("image/png"), "PNG", 0, 0, pageWidth, sliceHeight);
      }
      if (imageHeight === 0) throw new Error("Resume preview is empty.");
      pdf.save("CareerForge-Resume.pdf");
    } catch (error) {
      window.alert(error.message || "Unable to create the PDF.");
    } finally { setDownloading(false); }
  };
  return <AppShell {...{ user, page, onNavigate, onLogout }}><main className="content"><div className="page-heading"><span className="eyebrow">RESUME BUILDER</span><h1>Create a resume that feels like you.</h1><p>Complete the essentials, review the live layout, then export a clean PDF.</p></div><div className="resume-layout"><section className="resume-form">{[["name", "Full name", "Your full name"], ["email", "Email address", "you@example.com"], ["phone", "Phone", "Your phone number"], ["skills", "Skills", "Java, Spring Boot, React, SQL"]].map(([name, label, placeholder]) => <label key={name}>{label}<input name={name} value={resume[name]} onChange={change} placeholder={placeholder} /></label>)}{[["summary", "Professional summary"], ["education", "Education"], ["experience", "Experience"], ["projects", "Projects"]].map(([name, label]) => <label key={name}>{label}<textarea name={name} rows="4" value={resume[name]} onChange={change} placeholder={`Add your ${label.toLowerCase()} details`} /></label>)}<button className="button" onClick={download} disabled={downloading}>{downloading ? "Preparing PDF…" : "Download PDF"}</button></section><div><p className="preview-label">LIVE PREVIEW</p><ResumePreview resume={resume} /></div></div></main></AppShell>;
}
