import type { Metadata } from 'next'
import './globals.css'

export const metadata: Metadata = {
  title: 'Aurora Utils - Minecraft Editing Tool',
  description: 'An Axiom like tool for Minecraft editing, enhancing your building potential with powerful features.',
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  )
}
